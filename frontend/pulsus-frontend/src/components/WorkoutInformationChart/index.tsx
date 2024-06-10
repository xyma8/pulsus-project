import "./style.css"
import { LineChart, Line, XAxis, YAxis, Tooltip, AreaChart, Area, ResponsiveContainer } from 'recharts';
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
    const [valueY, setValueY] = useState<string | undefined>("--");

    function handleCurrentValueX(value: string | undefined) {
        props.onValueChange(value);
    }

    function handleCurrentValueY(value: string) {
        setValueY(value)
    }

    function handleMouseEnter() {
        handleCurrentValueX("on");
    }

    function handleMouseLeave() {
        setValueY("--");
        handleCurrentValueX(undefined);
    }

    function renderTooltip() {
        if(props.tooltipActive) {
            return(
                <Tooltip labelFormatter={(value: string) => `${handleCurrentValueX(value)}`} formatter={(value: string) => `${handleCurrentValueY(value)}`} wrapperStyle={{ display: 'none' }}/>
            )
        }
        else{
            if(valueY !== "--" ) {
                setValueY("--");
            }
            return null;
        }
    }

    function renderChart() {
        if(props.type === "line") {
            return(
            <div className="flex items-center p-4 text-text ">
                
                <div className="flex-1" onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
                    <div className="flex  justify-between">
                        <div className="font-semibold">
                            {props.YLabel}
                        </div>
                        <div className="font-semibold">
                            <CustomTooltipChart value={valueY} label={props.unit}/>
                        </div>
                    </div>
                    <LineChart width = {props.width} height = {130} data={props.data} syncId={props.syncId}>
                        <Line connectNulls type="monotone" dataKey="YAxis" name={props.YLabel} stroke={props.colorStroke} dot={false} />
                        <XAxis dataKey="XAxis" label={props.XLabel} tick={false} />
                        <YAxis />
                        {renderTooltip()}
                    </LineChart>

                    
                </div>

                
            </div>
            )
        } else if(props.type === "area") {
            return(
            <div className="flex items-center space-x-4 p-4 text-text">
                
                <div onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
                    <AreaChart width={props.width} height={props.height} data={props.data} syncId={props.syncId}>
                        <Area connectNulls type="monotone" dataKey="YAxis" name={props.YLabel} stroke={props.colorStroke} fill={props.colorStroke} dot={false} />
                        <XAxis dataKey="XAxis" label={props.XLabel} tick={false} />
                        <YAxis domain={props.yDomain} />
                        {renderTooltip()}
                    </AreaChart>
                </div>

             </div>
            )
        }
    }

    return(
    <div className="">
        {renderChart()}
    </div>
    )
}
//content={<CustomTooltipChart />}
//wrapperStyle={{ display: 'none' }}