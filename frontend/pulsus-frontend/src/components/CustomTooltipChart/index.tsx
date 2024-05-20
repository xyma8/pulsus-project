import "./style.css"
import { TooltipProps } from 'recharts';

type CustomTooltipChartProps = {
    CustomTooltip: React.FC<TooltipProps<number,string>>,
    label: string
}

const CustomTooltipChart: React.FC<TooltipProps<number, string>> = ({ active, payload }) => {

    if(active && payload) {
        return(
            <div className="custom-tooltip-chart-props">
                <p>{`${payload[0].value}`}</p>
            </div>
        )
    }else{
        return(
            <div className="custom-tooltip-chart-props">
                <p>-</p>
            </div>
        )
    }

    return(
    <div>
        
    </div>
    );
 };

 export default CustomTooltipChart;
    //{props.CustomTooltip?.defaultProps?.payload && <p>{`${props.label}: ${props.CustomTooltip.defaultProps?.payload[0].value} `}</p>}