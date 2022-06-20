import { useState } from "react";
import { FiLock, FiTrash2, FiX } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useAuth } from "../../hooks/useAuth";
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
  const [deleteAccount, setDeleteAccount] = useState<boolean>(false);
  const { user, handleClearUserDataFromStorage } = useAuth();
  const navigate = useNavigate();

  async function handleDeleteAccount() {
    try {
      await api.delete(`users/${user?.id}`);
      toast.success("Conta removida com sucesso");
      await handleClearUserDataFromStorage();
      navigate('/')
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
  }

  return (
    <>
      <Overlay hidden={!isVisible}>
        <ModalContainer>
          <FiX
            color="var(--red)"
            size={24}
            onClick={() => setIsVisible(false)}
          />
          <span className="profile">{user?.name.substring(0, 1)}</span>
          {user && (
            <>
              <Editable label="NOME:" value={user?.name} onSubmit={() => {}} />
              <Editable
                type="email"
                label="E-MAIL:"
                value={user?.username}
                onSubmit={() => {}}
              />
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
            <button type="button">
              <FiLock color="var(--white)" size={20} />
              <span>Alterar senha</span>
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
