import { FiTrash2, FiX } from "react-icons/fi";
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
            <Editable label="E-MAIL:" value={user?.email} onSubmit={() => {}} />
            <Editable
              label="SENHA:"
              value={user?.password}
              onSubmit={() => {}}
            />
          </>
        )}
        <button className="del-btn" >
          <FiTrash2 color="var(--white)" size={24} />
         <span> Excluir conta</span>
        </button>
      </ModalContainer>
    </Overlay>
  );
}
