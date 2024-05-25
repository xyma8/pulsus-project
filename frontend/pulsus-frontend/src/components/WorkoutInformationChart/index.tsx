import "./style.css"
import { LineChart, Line, XAxis, YAxis, Tooltip, AreaChart, Area } from 'recharts';
import { InformationChart } from "../../utils/projectTypes";
import { useState } from "react";
import CustomTooltipChart from "../CustomTooltipChart";

type WorkoutInformationChartProps = {
    type: string,
    data: InformationChart,
    width: number,
    height: number,
    XLabel?: string,
    YLabel: string,
    colorStroke: string,
    syncId: string,
    yDomain?: [number, number] //диапазон для Y
    unit: string
    tooltipActive: boolean
    onValueChange: (currentValue: string | undefined) => void
}

export default function WorkoutInformationChart(props: WorkoutInformationChartProps) {
    const [valueY, setValueY] = useState<string | undefined>();

    function handleCurrentValueX(value: string) {
        props.onValueChange(value);
    }

    function handleCurrentValueY(value: string) {
        setValueY(value)
    }

    function renderTooltip() {
        if(props.tooltipActive) {
            return(
                <Tooltip labelFormatter={(value: string) => `${handleCurrentValueX(value)}`} formatter={(value: string) => `${handleCurrentValueY(value)}`} wrapperStyle={{ display: 'none' }}/>
            )
        }
        else{
            
            return null;
        }
    }

    function renderChart() {
        if(props.type === "line") {
            return(
            <>
            <div className="workout-chart-info">
                {props.YLabel}
            </div>
            <LineChart width={props.width} height={props.height} data={props.data} syncId={props.syncId}>
                <Line connectNulls type="monotone" dataKey="YAxis" name={props.YLabel} stroke={props.colorStroke} dot={false} />
                <XAxis dataKey="XAxis" label={props.XLabel} />
                <YAxis />
                {renderTooltip()}
            </LineChart>
            <CustomTooltipChart value={valueY} label={props.unit}/>
            </>
            )
        } else if(props.type === "area") {
            return(
            <AreaChart width={props.width} height={props.height} data={props.data} syncId={props.syncId}>
                <Area connectNulls type="monotone" dataKey="YAxis" name={props.YLabel} stroke={props.colorStroke} fill={props.colorStroke} dot={false} />
                <XAxis dataKey="XAxis" label={props.XLabel} />
                <YAxis domain={props.yDomain} />
                {renderTooltip()}
             </AreaChart>
            )
        }
    }

    return(
    <div className="information-chart">
        {renderChart()}
    </div>
    )
}
//content={<CustomTooltipChart />}
//wrapperStyle={{ display: 'none' }}