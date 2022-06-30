import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useAuth } from "../../hooks/useAuth";
import { IQuestion } from "../../interfaces/question";
import api from "../../services/api";
import { handleNotifyError } from "../../utils/handleNotifyError";
import "./styles.scss";

interface Props {
  data: IQuestion;
  handleNextQuestion: (arg: number) => void;
  currentQuestion: number;
  totalQuestions: number;
}

export function Question({
  data,
  currentQuestion,
  totalQuestions,
  handleNextQuestion,
}: Props) {
  const [isAnswered, setIsAnswered] = useState<boolean>(false);
  const [selectedOption, setSelectedOption] = useState<string>("");
  const [result, setResult] = useState<'right' | 'wrong'>();
  const navigate = useNavigate();
  const { user } = useAuth();
  const { handleClearUserDataFromStorage } = useAuth();

  function nextQuestion() {
    if (currentQuestion < totalQuestions) {
      handleNextQuestion(currentQuestion + 1);
    }
  }

  async function handleSubmitAnswer() {
    try {
      const response = await api.post(`/questions/${data.id}/verifyAnswer`, { answer: selectedOption } );
      if(response.data.isCorrect){
        setResult('right')
        toast.success('Parabens! Você acertou a questão.');
      } else {
        setResult('wrong')
        toast.error('Que pena! Essa não é a opção correta.');
      }
      setIsAnswered(true);
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
  }

  return (
    <div className="question-container">
      <div className="line"></div>
      <h2>Exercício</h2>
      <div
        className="html-content"
        dangerouslySetInnerHTML={{ __html: data?.htmlContent as string }}
      ></div>
      <div className="alternatives">
        <div className="alternative">
          <input
            disabled={isAnswered || user?.roles.includes('ROLE_PROFESSOR') || user?.roles.includes('ROLE_ADMIN') }
            onClick={() => setSelectedOption('A')}
            type="radio"
            id="A"
            name="alternative"
          />
          <label className={ isAnswered && selectedOption === 'A'? result === 'right'? 'right' : 'wrong': 'normal' } htmlFor="A">
            {data.answerA}
          </label>
        </div>
        <div className="alternative">
          <input
            disabled={isAnswered || user?.roles.includes('ROLE_PROFESSOR') || user?.roles.includes('ROLE_ADMIN') }
            onClick={() => setSelectedOption("B")}
            type="radio"
            id="B"
            name="alternative"
          />
          <label className={ isAnswered && selectedOption === 'B'? result === 'right'? 'right' : 'wrong': 'normal' } htmlFor="B">
            {data.answerB}
          </label>
        </div>
        <div className="alternative">
          <input
            disabled={isAnswered || user?.roles.includes('ROLE_PROFESSOR') || user?.roles.includes('ROLE_ADMIN') }
            onClick={() => setSelectedOption("C")}
            type="radio"
            id="C"
            name="alternative"
          />
          <label className={ isAnswered && selectedOption === 'C'? result === 'right'? 'right' : 'wrong': 'normal' } htmlFor="C">
            {data.answerC}
          </label>
        </div>
        <div className="alternative">
          <input
            disabled={isAnswered || user?.roles.includes('ROLE_PROFESSOR') || user?.roles.includes('ROLE_ADMIN') }
            onClick={() => setSelectedOption("D")}
            type="radio"
            id="D"
            name="alternative"
          />
          <label className={ isAnswered && selectedOption === 'D'? result === 'right'? 'right' : 'wrong': 'normal' } htmlFor="D">
            {data.answerD}
          </label>
        </div>
        <div className="alternative">
          <input
            disabled={isAnswered || user?.roles.includes('ROLE_PROFESSOR') || user?.roles.includes('ROLE_ADMIN') }
            onClick={() => setSelectedOption("E")}
            type="radio"
            id="E"
            name="alternative"
          />
          <label className={ isAnswered && selectedOption === 'E'? result === 'right'? 'right' : 'wrong': 'normal' } htmlFor="E">
            {data.answerE}
          </label>
        </div>
      </div>
      <div className="controls">
        <button disabled={!selectedOption || isAnswered} onClick={handleSubmitAnswer}>
          Enviar Resposta
        </button>
        <button
          disabled={!(currentQuestion < totalQuestions) || !isAnswered}
          onClick={nextQuestion}
        >
          Próximo
        </button>
      </div>
    </div>
  );
}
