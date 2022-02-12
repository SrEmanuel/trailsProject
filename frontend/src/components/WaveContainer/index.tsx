import { ReactNode } from "react";
import { ReactComponent as WaveUp } from '../../assets/images/waveUp.svg';
import { ReactComponent as WaveDown } from '../../assets/images/waveDown.svg';
import './styles.scss';

interface Props {
    children: ReactNode
}

export function WaveContainer(props: Props){
 return(
    <div className="container">
        <WaveUp className="waveUp" />
        {props.children}
        <WaveDown className="waveDown" />
    </div>
 )
}