import { toast } from "react-toastify";

export async function useError(error:any, navigate:any, clear: any ){
  toast.error(error.response.data.message);
  if (error.response.data.status === 403) {
    await clear();
    navigate("/login");
  }
}