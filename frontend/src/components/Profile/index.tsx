import { FiX } from "react-icons/fi";
import { useAuth } from "../../hooks/useAuth";
import { ModalContainer } from "../ModalContainer";
import { Overlay } from "../Overlay";

import './styles.scss';

interface Props{
  isVisible: boolean;
  setIsVisible: (arg: boolean) => void;
}

export function Profile({ isVisible, setIsVisible }: Props){

  const { user } = useAuth();

  return(
    <Overlay hidden={!isVisible}>
      <ModalContainer>
        <FiX color="var(--red)" size={24} />
        <span className="profile">{user?.name.substring(0,1)}</span>
      </ModalContainer>
    </Overlay>
  )
}