import { Overlay } from "../../../../components/Overlay";

interface Props{
  isVisible: boolean;
}

export function AddNewSection({isVisible}: Props){
  return(
    <Overlay hidden={!isVisible} >
      the content
    </Overlay>
  )
}