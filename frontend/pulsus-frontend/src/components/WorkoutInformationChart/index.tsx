import "./style.css"
import { LineChart, Line, XAxis, YAxis, Tooltip } from 'recharts';
import { InformationChart } from "../../utils/projectTypes";
import CustomTooltipChart from "../CustomTooltipChart";

type WorkoutInformationChartProps = {
    data: InformationChart,
    width: number,
    height: number,
    XLabel: string,
    YLabel: string,
    colorStroke: string
}

export default function WorkoutInformationChart(props: WorkoutInformationChartProps) {
      
    return(
    <div className="information-chart">
        <LineChart width={props.width} height={props.height} data={props.data}>
            <Line type="monotone" dataKey="YAxis" name={props.YLabel} stroke={props.colorStroke} dot={false}/>
                <XAxis dataKey="XAxis" label={props.XLabel} />
                <YAxis label={{value: props.YLabel}}/>
                <Tooltip labelFormatter={(value: string) => `${value}`}/>
        </LineChart>
    </div>
    )
}
//content={<CustomTooltipChart />}
//wrapperStyle={{ display: 'none' }}