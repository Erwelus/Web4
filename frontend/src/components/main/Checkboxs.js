import {Checkbox} from "primereact/checkbox";
import React from "react";


function Checkboxs({x,setX,xs,string}) {

    function changeX_valsOrR_value(id) {
        setX(
            xs.map(x => {
                if(x.id === id){
                    x.check = !x.check
                }else if (string === "r"){
                    x.check =false;
                }
                return x
            })
        )
    }

    return(
        <span>
            {x.id + "-> "}
            <Checkbox checked={x.check} onChange={() => changeX_valsOrR_value(x.id)} />
            &nbsp;&nbsp;
        </span>
    )
}
export default Checkboxs
