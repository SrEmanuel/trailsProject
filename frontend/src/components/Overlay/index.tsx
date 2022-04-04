import { ReactNode } from "react";
import './styles.scss';

interface Props {
  children: ReactNode;
}

export function Overlay({ children }: Props) {
  return <div className="overlay">
    {children}
  </div>;
}
