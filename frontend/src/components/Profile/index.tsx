import { Formik } from "formik";
import { ChangeEvent, FormEvent, useRef, useState } from "react";
import { FiCamera, FiCheck, FiLock, FiSave, FiTrash2, FiX } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useAuth } from "../../hooks/useAuth";
import { IUser } from "../../interfaces/user";
import {
  EmailSchema,
  NameSchema,
  UpdatePasswordSchema,
} from "../../schemas/userUpdate.schema";
import api from "../../services/api";
import { handleNotifyError } from "../../utils/handleNotifyError";
import { ConfirmationModal } from "../ConfirmationModal";
import { Editable } from "../Editable";
import { ModalContainer } from "../ModalContainer";
import { Overlay } from "../Overlay";

import "./styles.scss";

interface Props {
  isVisible: boolean;
  setIsVisible: (arg: boolean) => void;
}

export function Profile({ isVisible, setIsVisible }: Props) {
  const [selectedFile, setSelectedFile] = useState<File>();
  const [selectedFilePreview, setSelectedFilePreview] = useState<string>();
  const [deleteAccount, setDeleteAccount] = useState<boolean>(false);
  const [changePassword, setChangePassword] = useState<boolean>(false);
  const { user, handleClearUserDataFromStorage, handleSavaUserDataToStorage } =
    useAuth();
  const formRef = useRef() as any;
  const navigate = useNavigate();

  function handleSubmit() {
    if (formRef.current) {
      formRef.current.handleSubmit();
    }
  }

  function handleFileUpload(e: ChangeEvent<HTMLInputElement>) {
    if (e.target.files) {
      setSelectedFile(e.target.files[0]);
      setSelectedFilePreview(URL.createObjectURL(e.target.files[0]));
    }
  }

  function handleClearFileSelection(){
    setSelectedFile(undefined);
    setSelectedFilePreview(undefined);
  }

  async function handleUpdateProfilePicture(e: FormEvent ){
    e.stopPropagation();
    const data = new FormData();
    data.append('image', selectedFile as File );
    try {
      const response = await api.post(`/users/${user?.id}/add-image`, data);
      handleClearFileSelection();
      handleSavaUserDataToStorage(response.data, user?.token as string );
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage)
    }
  }

  async function handleDeleteAccount() {
    try {
      await api.delete(`users/${user?.id}`);
      toast.success("Conta removida com sucesso");
      await handleClearUserDataFromStorage();
      navigate("/");
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
  }

  async function handleUpdateAccount(
    data: Partial<IUser> | any,
    atribute: string
  ) {
    try {
      const response = await api.put(
        `/users/${user?.id}/update/${atribute}`,
        data
      );
      toast.success("Atualizado com sucesso!");
      if (atribute === "name") {
        await handleSavaUserDataToStorage(response.data, user?.token as string);
      } else {
        toast.info("Faço login novamente com as novas credenciais");
        await handleClearUserDataFromStorage();
        navigate("/login");
      }
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
  }

  return (
    <>
      <Overlay hidden={!isVisible}>
        <ModalContainer customStyle="profile-container">
          <FiX
            color="var(--red)"
            size={24}
            onClick={() => setIsVisible(false)}
          />
          <div className="profile">
           { selectedFile ? (
            <button onClick={handleUpdateProfilePicture}>
              <FiCheck />
            </button>
           ) : (
             <label htmlFor="profile-picture" >
             <FiCamera color="var(--white)" size={24} />
           </label>
           ) }
            <input id="profile-picture" type="file" accept="image/*" onChange={handleFileUpload} />
            { selectedFile || user?.imagePath ? (
              <img src={selectedFilePreview || user?.imagePath } alt={selectedFile?.name} />
            ) : (
              <span>{user?.name.substring(0, 1)}</span>
            ) }
          </div>
          {user && (
            <>
              <Editable
                label="NOME:"
                value={user?.name}
                onSubmit={(value) =>
                  handleUpdateAccount({ name: value }, "name")
                }
                validator={NameSchema}
              />
              <Editable
                type="email"
                label="E-MAIL:"
                value={user?.username}
                onSubmit={(value) =>
                  handleUpdateAccount({ email: value }, "email")
                }
                validator={EmailSchema}
              />
              {changePassword && (
                <Formik
                  innerRef={formRef}
                  initialValues={{
                    oldPassword: "",
                    password: "",
                    confirmPassword: "",
                  }}
                  validationSchema={UpdatePasswordSchema}
                  onSubmit={(values) =>
                    handleUpdateAccount(
                      {
                        password: values.password,
                        oldPassword: values.oldPassword,
                      },
                      "password"
                    )
                  }
                >
                  {({ handleChange, errors, touched }) => (
                    <form className="change-password">
                      <span>Alterar senha</span>
                      <label htmlFor="oldPassword">SENHA ANTIGA:</label>
                      <input
                        type="password"
                        name="oldPassword"
                        onChange={handleChange}
                      />
                      {errors.oldPassword && touched.oldPassword && (
                        <span className="error text">{errors.oldPassword}</span>
                      )}

                      <label htmlFor="password">NOVA SENHA:</label>
                      <input
                        type="password"
                        name="password"
                        onChange={handleChange}
                      />
                      {errors.password && touched.password && (
                        <span className="error text">{errors.password}</span>
                      )}

                      <label htmlFor="confirmPassword">CONFIRMAR:</label>
                      <input
                        type="password"
                        name="confirmPassword"
                        onChange={handleChange}
                      />
                      {errors.confirmPassword && touched.confirmPassword && (
                        <span className="error text">
                          {errors.confirmPassword}
                        </span>
                      )}
                    </form>
                  )}
                </Formik>
              )}
            </>
          )}
          <div className="row-wrapper bottom">
            <button
              type="button"
              className="del-btn"
              onClick={() => {
                setDeleteAccount(true);
                setIsVisible(false);
              }}
            >
              <FiTrash2 color="var(--white)" size={20} />
              <span> Excluir conta</span>
            </button>
            <button
              type="button"
              onClick={() =>
                !changePassword ? setChangePassword(true) : handleSubmit()
              }
            >
              {!changePassword ? (
                <>
                  <FiLock color="var(--white)" size={20} />
                  <span>Alterar senha</span>
                </>
              ) : (
                <>
                  <FiSave color="var(--white)" size={20} />
                  <span>Salvar senha</span>
                </>
              )}
            </button>
          </div>
        </ModalContainer>
      </Overlay>
      <ConfirmationModal
        isVisible={deleteAccount}
        setIsVisible={setDeleteAccount}
        onConfirm={handleDeleteAccount}
        message="Deseja mesmo deletar sua conta?"
        confirmText="Sim, deletar"
        cancelText="Não, cancelar"
      />
    </>
  );
}
