import { AxiosResponse } from "axios";

import axios from "axios";
import { toast } from "react-toastify";

const api = axios.create({
  baseURL:
    process.env.NODE_ENV === "production"
      ? process.env.REACT_APP_API_URL
      : process.env.REACT_APP_LOCAL_API_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.response.use(
  (response: AxiosResponse) => {
    return response;
  },

  (error: any) => {
    if (error.message === "Network Error") {
      toast.warning("Você não parece estar conectado a rede. Tente novamente mais tarde");
      //Navigate('Landing',{message:'connection error'})
    }

    if (error.response.status === 401 || error.response.status === 403) {
      const requestConfig = error.config;
      //Navigate('Login',{})

      return axios(requestConfig);
    }

    return Promise.reject(error);
  }
);

api.interceptors.request.use(
  (config: any) => {
    const userData = localStorage.getItem("user");
    if (userData) {
      const token = JSON.parse(userData).token;
      config.headers.authorization = token;
    }
    return Promise.resolve(config);
  },

  (error: any) => {
    return Promise.reject(error);
  }
);

export default api;
