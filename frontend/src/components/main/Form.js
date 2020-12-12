import React, {useRef} from "react"
import store from "../../storage/storage";
import {Button} from "primereact/button";
import {Messages} from "primereact/messages";
import {InputNumber} from "primereact/inputnumber";
import Checkboxs from "./Checkboxs";


function Form(props) {

    const onSubmit = () => {
        props.x.map(x => {
            if (x.check === true){
                if (props.validate()) {
                    fetch("/api/app/area", {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json;charset=utf-8',
                            'Authorization': `Bearer_${store.getState().token}`
                        },
                        body: JSON.stringify({x: x.id, y: props.y, r: getR()})
                    }).then(response => response.text().then(text => {
                        if (response.ok) {
                            props.setResults(JSON.parse(text))
                        }
                        if (response.status == 403) {
                            fetch("/api/refresh/token", {
                                method: 'POST',
                                headers: {'Content-Type': 'application/json'},
                                body: JSON.stringify({refresh_token: sessionStorage.getItem("refresh_token")})
                            }).then(response => response.json().then(rtoken => {
                                if (response.ok) {
                                    store.dispatch({type: "TOKEN_UPDATE", token: rtoken.token})
                                    fetch("/api/app/area", {
                                        method: 'POST',
                                        headers: {
                                            'Content-Type': 'application/json;charset=utf-8',
                                            'Authorization': `Bearer_${store.getState().token}`
                                        },
                                        body: JSON.stringify({x: x.id, y: props.y, r: getR()})
                                    }).then(response => response.text().then(text => {
                                        if (response.ok) {
                                            props.setResults(JSON.parse(text))
                                        } else {
                                            props.messageText.current.show({
                                                severity: 'error',
                                                summary: 'Refresh token error'
                                            })
                                        }
                                    }))
                                } else {
                                    props.messageText.current.show({
                                        severity: 'error',
                                        summary: 'Refresh token error'
                                    })
                                }
                            }))
                        }
                    }))
                }
            }
        })
    }

    function getR() {
        let R = 0;
        props.r.map(r_1 => {
            if(r_1.check === true){
                R = r_1.id
            }
        })
        return R
    }


    const onClear = () => {
        fetch("/api/app/clear", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
                'Authorization': `Bearer_${store.getState().token}`
            }
        }).then(response => {
            if (response.ok) {
                props.setResults(null)
            }
            if (response.status == 403) {
                fetch("/api/refresh/token", {
                    method: 'POST',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({refresh_token: sessionStorage.getItem("refresh_token")})
                }).then(response => response.json().then(rtoken => {
                    if (response.ok) {
                        store.dispatch({type: "TOKEN_UPDATE", token: rtoken.token})
                        fetch("/api/app/clear", {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json;charset=utf-8',
                                'Authorization': `Bearer_${store.getState().token}`
                            }
                        }).then(response => {
                            if (response.ok) {
                                props.setResults(null)
                            } else {
                                props.messageText.current.show({
                                    severity: 'error',
                                    summary: 'Refresh token error'
                                })
                            }
                        })
                    } else {
                        props.messageText.current.show({
                            severity: 'error',
                            summary: 'Refresh token error'
                        })
                    }
                }))
            }
        })
    }

    return (
        <div className="p-align-center p-fluid">
            <form>
                <Messages ref={(el) => props.messageText.current = el}/>
                <div className="p-field p-grid"     >
                    <div className="p-field p-grid ">
                        <br/>
                        <label htmlFor="x_value" className="p-sm-2 p-md-4 p-xl-6">X:</label>
                            <span className="p-sm-12 p-md-7 p-xl-5 p-col" >
                                {props.x.map(x =>{
                                    return <Checkboxs  x={x} setX={props.setX} xs={props.x} key={x.id} string={"x"}/>
                                })}
                            </span>
                    </div>

                    <div className="p-field p-grid ">
                        <label htmlFor="y_value" className="p-sm-2 p-md-4 p-xl-6">Y:</label>
                        <div className="p-sm-12 p-md-7 p-xl-5 p-col">
                            <InputNumber id="y_value" value={props.y} onValueChange={(e) => props.setY(e.value)} mode="decimal"
                                         min={-3} max={5}
                                         minFractionDigits={1} maxFractionDigits={5} placeholder="Enter Y [-3;5]"/>
                        </div>
                    </div>

                    <div className="p-field p-grid ">
                        <br/>
                        <label htmlFor="r_value" className="p-sm-2 p-md-4 p-xl-6">R:</label>
                        <span className="p-sm-12 p-md-7 p-xl-5 p-col" >
                            {props.r.map(r =>{
                                return <Checkboxs  x={r} setX={props.setR} xs={props.r} key={r.id} string={"r"} />
                            })}
                        </span>
                    </div>
                    <br/>

                    <div className="p-sm-12 p-md-6  p-align-center p-col">
                        <Button type="button" onClick={onSubmit} className="p-button-primary p-margin" label="Submit" icon="pi"/>
                    </div>

                    <br/>

                    <div className="p-sm-12 p-md-6 p-xl-3 p-align-center p-col" >
                        <Button type="button" onClick={onClear} className="p-button-primary" label="Clear" icon="pi"/>
                    </div>
                </div>
            </form>
        </div>
        )

}


export default Form;
