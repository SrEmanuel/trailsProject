import { NavBar } from "../../components/Navbar";
import "./styles.scss";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { ISubject } from "../../interfaces/subject";
import api from "../../services/api";

export function BlogPost() {
  const [subject, setSubject] = useState<ISubject>()

  const params = useParams();

  useEffect(() => {
    async function handleLoadSubject(){
      const response = await api.get(`/subjects/${params.blogtitle}`);
      const tempSubject: ISubject = response.data;
      !tempSubject.completed && await api.put(`/subjects/${tempSubject.linkName}/user/mark?state=true`);
      setSubject(tempSubject);
    }

    handleLoadSubject();

  }, [params])

  return (
    <div className="container">
      <NavBar />
      <h1>{subject?.name}</h1>
      <div className="html-content" dangerouslySetInnerHTML={{__html:subject?.htmlContent as string }} >
       
      </div>
    </div>
  );
}
