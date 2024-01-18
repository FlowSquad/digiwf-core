import { Configuration, SigningApiFactory, SigningModel } from "@/api";
import { AxiosResponse } from "axios";

export const getSigningUrl = (basePath: string): Promise<SigningModel> => {
  const cfg = axiosConfig(basePath);
  return SigningApiFactory(cfg)
    .createSigning()
    .then((response: AxiosResponse<SigningModel>) => Promise.resolve(response.data));
}

const axiosConfig = (basePath: string): Configuration => {
  const cfg = new Configuration();
  cfg.basePath = basePath;
  return cfg;
}
