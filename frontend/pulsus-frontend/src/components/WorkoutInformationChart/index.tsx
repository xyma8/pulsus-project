import "./style.css"
import { LineChart, Line, XAxis, YAxis, Tooltip } from 'recharts';
import { InformationChart } from "../../utils/projectTypes";
import { useState } from "react";
import CustomTooltipChart from "../CustomTooltipChart";

type WorkoutInformationChartProps = {
    data: InformationChart,
    width: number,
    height: number,
    XLabel: string,
    YLabel: string,
    colorStroke: string,
    syncId: string,
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


    return(
    <div className="information-chart">
        <LineChart width={props.width} height={props.height} data={props.data} syncId={props.syncId}>
            <Line connectNulls type="monotone" dataKey="YAxis" name={props.YLabel} stroke={props.colorStroke} dot={false} />
                <XAxis dataKey="XAxis" label={props.XLabel} />
                <YAxis label={{value: props.YLabel}} />
                {renderTooltip()}
        </LineChart>

        <CustomTooltipChart value={valueY} label={props.unit}/>
        
    </div>
    )
}
//content={<CustomTooltipChart />}
//wrapperStyle={{ display: 'none' }}