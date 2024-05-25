import "./style.css"
import { TooltipProps } from 'recharts';

type CustomTooltipChartProps = {
    value: string | undefined
    label?: string
}

export default function CustomTooltipChart(props: CustomTooltipChartProps) {
    return(
    <div className="custom-tooltip-chart">
        {props.value ? (<span>{props.value}</span>) : (<span>--</span>)}
        {props.label}
    </div>
    );
 };
    //{props.CustomTooltip?.defaultProps?.payload && <p>{`${props.label}: ${props.CustomTooltip.defaultProps?.payload[0].value} `}</p>}