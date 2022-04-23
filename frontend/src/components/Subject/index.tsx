import "./styles.scss";
import { Link, useNavigate } from "react-router-dom";
import { ISubject } from "../../interfaces/subject";
import { FiEdit, FiTrash2 } from "react-icons/fi";
import { FormEvent, useState } from "react";
import { DeleteSubject } from "../DeleteSubject";

interface Props {
  subject: ISubject;
  coursename: string;
  showOptions: boolean;
}

export function Subject({ subject, coursename, showOptions }: Props) {
  const [isDeleteModalVisible, setIsDeleteModalVisible] = useState(false);
  const navigate = useNavigate();

  function toggleDeleteModal(event: FormEvent) {
    event.stopPropagation();
    setIsDeleteModalVisible(!isDeleteModalVisible);
  }

  return (
    <div className="card-container subject">
      <DeleteSubject
        isVisible={isDeleteModalVisible}
        setIsVisible={setIsDeleteModalVisible}
        selectedSubjectLinkName={subject.linkName}
      />
      <div
        className="card-header"
        onClick={() => navigate(`/cursos/${coursename}/${subject.linkName}`)}
      >
        <img src={subject.imagePath} alt="capa do card" />
        <Link to="#">{subject.grade}</Link>
      </div>
      <div className="card-line"></div>
      <div className="subject-card-bottom">
        <span>
          {subject.position}-{subject.name}
        </span>
        {showOptions && (
          <div>
            <FiEdit onClick={() => navigate(`/cursos/${coursename}/${subject.linkName}/atualizar`)} color="var(--purple)" size={24} />
            <FiTrash2
              onClick={toggleDeleteModal}
              color="var(--red)"
              size={24}
            />
          </div>
        )}
      </div>
    </div>
  );
}
