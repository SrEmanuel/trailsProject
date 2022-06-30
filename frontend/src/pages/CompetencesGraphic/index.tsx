import { useEffect, useState } from "react";
import { FiInfo } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { CompetenceDetails } from "../../components/CompetenceDetails";
import { NavBar } from "../../components/Navbar";
import { useAuth } from "../../hooks/useAuth";
import api from "../../services/api";
import { handleNotifyError } from "../../utils/handleNotifyError";
import "./styles.scss";

interface CompetencePoints {
  competence: string;
  description: string;
  points: number;
  total: number;
  id: number;
}

export function CompetencesGraphic() {
  const [competencePoints, setCompetencePoints] = useState<CompetencePoints[]>(
    []
  );
  const [isVisible, setIsVisible] = useState<boolean>(false);
  const [selectedCompetence, setSelectedCompetence] = useState<{
    name: string;
    description: string;
  }>();
  const { handleClearUserDataFromStorage, user } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    async function handleLoadCompetencesProgress() {
      try {
        const response = await api.get(`/users/${user?.id}`);
        console.log(response.data.competencePoints);
        setCompetencePoints(response.data.competencePoints);
      } catch (error) {
        handleNotifyError(error, navigate, handleClearUserDataFromStorage);
      }
    }

    handleLoadCompetencesProgress();
  }, [handleClearUserDataFromStorage, navigate, user]);

  return (
    <div className="container">
      <NavBar />
      <h1>Seu desempenho em cada competência</h1>

      <div className="scoreList">
        {competencePoints?.map((competence) => (
          <div key={competence.id} className="score">
            <span>
              <FiInfo
                color="var(--grey)"
                size={18}
                onClick={() => {
                  setIsVisible(true);
                  setSelectedCompetence({
                    name: competence.competence,
                    description: competence.description,
                  });
                }}
              />
              {competence.competence}
            </span>
            <div className="total-score">
              <div
                style={{
                  width: `${Math.round(
                    (competence.points / competence.total) * 100
                  )}%`,
                }}
                className="points"
              ></div>
            </div>
            <span>
              {" "}
              {`${Math.round(
                (competence.points / competence.total) * 100
              )}%`}{" "}
            </span>
          </div>
        ))}
      </div>

      <CompetenceDetails
        isVisible={isVisible}
        setIsVisible={setIsVisible}
        name={selectedCompetence?.name as string }
        description={selectedCompetence?.description as string }
      />
    </div>
  );
}
