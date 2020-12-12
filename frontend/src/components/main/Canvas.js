import React, {useEffect, useRef} from "react"
import {clickPoint, draw, drawPoints} from "./draw"
import Checkboxs from "./Checkboxs";

function Canvas(props) {
    const canvas = useRef()

    function maxR(){
        let maxR = 0;
        {props.r.map(r =>{
            if(r.check === true){
                maxR = r.id
            }
        })}
        return maxR;
    }
    useEffect(() => {
        draw(maxR(), canvas.current.getContext("2d"));
        if(props.results != null) {
            drawPoints(maxR(), props.results, canvas.current.getContext("2d"))
        }
    }, [draw, drawPoints, props]);
    return <div>

        <canvas className="p-align-center" width="750" height="500" ref={canvas} onClick={(e) => {
            clickPoint(e, canvas.current.getContext("2d"),maxR(), props.validateNumber, props.messageText, props.setResults)
        }}/>
    </div>
}

export default Canvas;