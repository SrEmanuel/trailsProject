import { ReactNode } from "react";
import './styles.scss';

interface Props {
  children: ReactNode;
  hidden: boolean
}

export function Overlay({ children, hidden }: Props) {
  return <div className={hidden? 'hidden-overlay' : 'overlay'}>
    {children}
  </div>;
}
