import { NavigateFunction } from "react-router-dom";
import { toast } from "react-toastify";

export async function handleNotifyError(error:any, navigate: NavigateFunction , clear: () => Promise<void> ){
  toast.error(error.response.data.message);
  if (error.response.data.status === 403 || error.response.data.status === 401 ) {
    await clear();
    navigate("/login");
  }
}