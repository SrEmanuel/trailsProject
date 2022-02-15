import { NavBar } from "../../components/Navbar";
import "./styles.scss";
import { Link, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { ISubject } from "../../interfaces/subject";
import api from "../../services/api";

export function BlogPost() {
  const [subject, setSubject] = useState<ISubject>()

  const params = useParams();

  useEffect(() => {
    async function handleLoadSubject(){
      const response = await api.get(`/subjects/${params.blogid}`);
      setSubject(response.data);
    }

    handleLoadSubject()

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
