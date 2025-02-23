<template>
  <v-autocomplete
    :aria-required="isRequired()"
    :filter="filterUsers"
    :items="entries"
    :label="label"
    :loading="isLoading"
    :no-data-text="noDataText"
    :readonly="isReadonly"
    :rules="rules ? rules : true"
    :search-input.sync="search"
    :value="model"
    auto-select-first
    item-text="lhmObjectId"
    item-value="lhmObjectId"
    placeholder="Benutzer suchen..."
    v-bind="schema['x-props']"
    @input="input"
  >
    <template #label>
      <span>{{ label }}</span>
      <span v-if="isRequired()" aria-hidden="true" style="font-weight: bold; color: red"> *</span>
    </template>
    <template #selection="data">
      {{ getFullName(data.item) }}
    </template>
    <template #item="data">
      <template>
        <v-list-item-avatar class="avatar">
          {{ getNamePrefix(data.item) }}
        </v-list-item-avatar>
        <v-list-item-content>
          <v-list-item-title>{{ getFullName(data.item) }}</v-list-item-title>
          <v-list-item-subtitle>{{ castNoAttrAvailable(data.item.email) }}</v-list-item-subtitle>
          <v-list-item-subtitle>
            {{ castNoAttrAvailable(data.item.department) }} <span class="dot">&#8226;</span>
            {{ castNoAttrAvailable(data.item.ou) }}
          </v-list-item-subtitle>
        </v-list-item-content>
      </template>
    </template>
  </v-autocomplete>
</template>

<style scoped>
.avatar {
  background-color: gray;
  color: white;
}

.dot {
  margin: 0 0.2rem;
  font-size: 1.2rem;
  line-height: 1rem;
}
</style>

<script lang="ts">
import {Component, Prop, Vue, Watch} from "vue-property-decorator";
import {VAutocomplete} from "vuetify/lib";
import {FetchUtils, SearchUserTO, UserRestControllerApiFactory, UserTO} from '@muenchen/digiwf-engine-api-internal';
import {AxiosResponse} from 'axios';
import {ApiConfig} from "../../api/ApiConfig";
import {checkRequired} from "@/components/schema/validation/required";

@Component({
  components: {
    VAutocomplete
  }
})
export default class VUserInput extends Vue {

  search = "";
  items: UserTO[] = [];
  isLoading = false;
  errorMessage = "";
  model = "";
  lastSearch = "";
  noDataText = "Tippen, um Suche zu starten";
  locked = false;
  readonly = false;
  ldapOus: string | undefined;

  @Prop()
  value: string | undefined;

  @Prop()
  col: string | undefined;

  @Prop()
  label: string | undefined;

  @Prop()
  rules: any | undefined

  @Prop()
  on: any;

  @Prop()
  schema: any

  get isReadonly(): boolean {
    return this.readonly || this.locked;
  }

  get entries(): UserTO [] {
    return this.items;
  }

  input(value: any): any {
    return this.on.input(value);
  }

  created(): void {
    const schemaobj = JSON.parse(JSON.stringify(this.schema));
    this.readonly = schemaobj.readOnly;
    this.ldapOus = schemaobj['ldap-groups'];

    if (this.value) {
      this.loadInitialValue(this.value);
    }
  }

  async loadInitialValue(id: string): Promise<void> {
    try {
      this.locked = true;
      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
      let res: AxiosResponse;

      //if number search by objectId, if string search by username
      if (id.match(/^-?\d+$/)) {
        //user = await UserService.searchUser(id);
        res = await UserRestControllerApiFactory(cfg).getUser(id);
      } else {
        //user = await UserService.searchUserByUid(id);
        res = await UserRestControllerApiFactory(cfg).getUserByUsername(id);
      }
      const user = res.data;

      this.items.push(user);
      this.model = user.lhmObjectId!;
      this.input(user.lhmObjectId);
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = 'Der Benutzer konnte nicht geladen werden.';
      this.model = "";
      this.input("");
    }
    this.locked = false;
  }

  getFullName(user: UserTO): string {
    return user.forename + " " + user.surname;
  }

  filterUsers(item: UserTO, queryText: string): boolean {
    const fullName = this.getFullName(item);
    return fullName.toLowerCase().includes(queryText.toLowerCase());
  }

  getNamePrefix(user: UserTO): string {
    return user.forename!.slice(0, 1) + user.surname!.slice(0, 1);
  }

  castNoAttrAvailable(val: string): string {
    if (val === "No_Attribute_Available") {
      return "-";
    }
    return val;
  }

  isRequired() {
    return checkRequired(this.schema);
  }


  @Watch("search")
  async queryResults(): Promise<void> {

    if (!this.search || this.search.length < 3) return;

    if (this.lastSearch === this.search.slice(0, 3)) return;

    this.lastSearch = this.search.slice(0, 3);

    try {
      this.noDataText = "Benutzer werden gesucht...";
      this.isLoading = true;

      const to: SearchUserTO = {
        searchString: this.lastSearch,
        ous: this.ldapOus ? this.ldapOus : undefined
      };

      const cfg = ApiConfig.getAxiosConfig(FetchUtils.getGETConfig());
      const res = await UserRestControllerApiFactory(cfg).getUsers(to);

      if (this.lastSearch === this.search.slice(0, 3)) {
        this.items = res.data;
      }
      this.errorMessage = "";
    } catch (error) {
      this.errorMessage = 'Der Benutzer konnte nicht geladen werden.';
    }
    this.isLoading = false;
    this.noDataText = "Tippen, um Suche zu starten";
  }

}
</script>

