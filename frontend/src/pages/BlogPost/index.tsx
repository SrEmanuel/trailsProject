import { NavBar } from "../../components/Navbar";
import "./styles.scss";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { ISubject } from "../../interfaces/subject";
import api from "../../services/api";
import { handleNotifyError } from "../../utils/handleNotifyError";
import { useAuth } from "../../hooks/useAuth";
import { Question } from "../../components/Question";
import questions from "./db.json";

export function BlogPost() {
  const [subject, setSubject] = useState<ISubject>();
  const [currentQuestion, setCurrentQuestion] = useState<number>(0);
  const navigate = useNavigate();
  const { handleClearUserDataFromStorage, getUser } = useAuth();
  const params = useParams();

  useEffect(() => {
    async function handleLoadSubject() {
      try {
        const response = await api.get(`/subjects/${params.blogtitle}`);

        const tempSubject: ISubject = response.data;

        if (!tempSubject.completed && getUser()) {
          const url = `/subjects/${tempSubject.linkName}/user/mark?state=true`;
          await api.put(url);
        }

        setSubject(tempSubject);
      } catch (error) {
        handleNotifyError(error, navigate, handleClearUserDataFromStorage);
      }
    }

    handleLoadSubject();
  }, [getUser, handleClearUserDataFromStorage, navigate, params]);

  return (
    <div className="container">
      <NavBar />
      <h1>{subject?.name}</h1>
      <div
        className="html-content"
        dangerouslySetInnerHTML={{ __html: subject?.htmlContent as string }}
      ></div>
      <Question data={questions[currentQuestion].question} currentQuestion={currentQuestion} totalQuestions={questions.length - 1} handleNextQuestion={setCurrentQuestion} />
    </div>
  );
}
