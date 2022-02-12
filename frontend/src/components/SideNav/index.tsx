import { useState } from "react";
import { FiMenu } from "react-icons/fi";
import "./styles.scss";

export function SideNav() {
  const [isVisible, setIsVisible] = useState(false);

  function toggleSidebar() {
    setIsVisible(!isVisible);
  }

  return (
    <>
      <FiMenu
        onClick={toggleSidebar}
        className="hamburguer"
        color="var(--green)"
        size={24}
      />

      <div className={`menu ${isVisible ? "active" : ""}`}></div>
    </>
  );
}
