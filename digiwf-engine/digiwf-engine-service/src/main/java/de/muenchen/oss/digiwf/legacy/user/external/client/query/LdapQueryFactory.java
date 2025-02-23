/*
 * Copyright (c): it@M - Dienstleister für Informations- und Telekommunikationstechnik der Landeshauptstadt München, 2020
 */
package de.muenchen.oss.digiwf.legacy.user.external.client.query;

import de.muenchen.oss.digiwf.legacy.user.external.mapper.LdapAttributeConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.SearchScope;

import java.util.List;

import static de.muenchen.oss.digiwf.legacy.user.external.mapper.LdapAttributeConstants.LDAP_CN;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * Create predefines ldap queries.
 */
@RequiredArgsConstructor
public class LdapQueryFactory {

    private final LdapFilterFactory ldapFilterFactory;
    private final String personSearchBase;
    private final String ouSearchBase;

    public LdapQuery createPersonByIdQuery(final String id) {
        return query()
                .searchScope(SearchScope.SUBTREE)
                .base(personSearchBase)
                .where(LdapAttributeConstants.LDAP_OBJID).is(id);
    }

    public LdapQuery createPersonByUsernameQuery(final String username) {
        return query()
                .searchScope(SearchScope.SUBTREE)
                .base(personSearchBase)
                .where(LdapAttributeConstants.LDAP_UID).is(username);
    }

    public LdapQuery createPersonByNamePatternAndOuQuery(final String filter, final List<String> ouFilters) {
        return query()
                .searchScope(SearchScope.SUBTREE)
                .base(personSearchBase)
                .filter(new AndFilter()
                        .append(ldapFilterFactory.createOuNameFilter(ouFilters))
                        .append(ldapFilterFactory.createNamePatternFilter(filter))
                        .append(ldapFilterFactory.createPersonFilter()));
    }

    public LdapQuery createPersonByNamePatternsAndOuQuery(final String firstFilter, final String secondFilter, final List<String> ouFilters) {
        return query()
                .searchScope(SearchScope.SUBTREE)
                .base(personSearchBase)
                .filter(new AndFilter()
                        .append(ldapFilterFactory.createOuNameFilter(ouFilters))
                        .append(ldapFilterFactory.createNamePatternsFilter(firstFilter, secondFilter))
                );
    }

    public LdapQuery createOuByShortNameQuery(final String shortName) {
        return query()
                .searchScope(SearchScope.SUBTREE)
                .base(ouSearchBase)
                .where(LdapAttributeConstants.LDAP_SHORTNAME).is(shortName);
    }

    public LdapQuery createOuTreeByShortcodeQuery(final String ouShortCode) {
        return query()
                .searchScope(SearchScope.SUBTREE)
                .base(this.personSearchBase)
                .countLimit(1)
                .where(LDAP_CN).is(ouShortCode);
    }

}
