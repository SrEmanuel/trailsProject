import './styles.scss';
import {ReactComponent as Previous} from '../../assets/images/previous.svg';
import {ReactComponent as Next} from '../../assets/images/next.svg';

export function Paginator(){
    return(
        <div className="paginator">
            <Previous />
            Página 1 de 3
            <Next />
        </div>
    )
}