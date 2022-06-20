import { FiLock, FiTrash2, FiX } from "react-icons/fi";
import { useAuth } from "../../hooks/useAuth";
import { Editable } from "../Editable";
import { ModalContainer } from "../ModalContainer";
import { Overlay } from "../Overlay";

import "./styles.scss";

interface Props {
  isVisible: boolean;
  setIsVisible: (arg: boolean) => void;
}

export function Profile({ isVisible, setIsVisible }: Props) {
  const { user } = useAuth();

  return (
    <Overlay hidden={!isVisible}>
      <ModalContainer>
        <FiX color="var(--red)" size={24} onClick={() => setIsVisible(false)} />
        <span className="profile">{user?.name.substring(0, 1)}</span>
        {user && (
          <>
            <Editable label="NOME:" value={user?.name} onSubmit={() => {}} />
            <Editable type="email" label="E-MAIL:" value={user?.username} onSubmit={() => {}} />
          </>
        )}
        <div className="row-wrapper bottom">
        <button type="button" className="del-btn" >
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
  );
}
