import { ReactNode } from "react";
import { ReactComponent as Route } from '../../assets/images/route.svg';
import { ReactComponent as Tree } from '../../assets/images/tree.svg';
import './styles.scss';

interface Props {
    children: ReactNode
}

export function ForestContainer(props: Props){
 return(
    <div className="forest-container">
        <Tree className="tree first" />
        <Tree className="tree second" />
        {props.children}
        <Route className="route" />
        <Tree className="tree third" />
    </div>
 )
}