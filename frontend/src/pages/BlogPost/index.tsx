import { NavBar } from "../../components/Navbar";
import "./styles.scss";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { ISubject } from "../../interfaces/subject";
import api from "../../services/api";
import { handleNotifyError } from "../../utils/handleNotifyError";
import { useAuth } from "../../hooks/useAuth";
import { Question } from "../../components/Question";
import { IQuestion } from "../../interfaces/question";

export function BlogPost() {
  const [subject, setSubject] = useState<ISubject>();
  const [currentQuestion, setCurrentQuestion] = useState<number>(0);
  const [questions, setQuestions] = useState<IQuestion[]>([]);
  const navigate = useNavigate();
  const { handleClearUserDataFromStorage, getUser, getIsTeacher } = useAuth();
  const params = useParams();

  useEffect(() => {
    async function handleLoadSubject() {
      try {
        const response = await api.get(`/subjects/${params.blogtitle}`);
        setQuestions(response.data.questions);
        const tempSubject: ISubject = response.data;

        if (!tempSubject.completed && getUser() && !getIsTeacher ) {
          const url = `/subjects/${tempSubject.linkName}/user/mark?state=true`;
          await api.put(url);
        }

        setSubject(tempSubject);
      } catch (error) {
        handleNotifyError(error, navigate, handleClearUserDataFromStorage);
      }
    }

    handleLoadSubject();
  }, [getIsTeacher, getUser, handleClearUserDataFromStorage, navigate, params]);

  return (
    <div className="container">
      <NavBar />
      <h1>{subject?.name}</h1>
      <div
        className="html-content"
        dangerouslySetInnerHTML={{ __html: subject?.htmlContent as string }}
      ></div>
      { questions[0] && <Question data={questions[0]} currentQuestion={currentQuestion} totalQuestions={questions.length - 1} handleNextQuestion={setCurrentQuestion} /> }
    </div>
  );
}
