import React, {Component} from 'react';
import HomePage from "./components/main/HomePage";
import LoginForm from "./components/login/LoginForm";
import {Panel} from "primereact/panel";
import 'primereact/resources/themes/bootstrap4-dark-blue/theme.css';
import 'primereact/resources/primereact.css';


class App extends Component {

    componentDidMount(){
        this.props.store.subscribe(() => {
            this.setState({reduxState: this.props.store.getState()});
        })
    }

    render() {
        return (
            <div className="p-grid p-justify-center wrapper">
                <div className="p-col p-sm-12 p-md-8 p-xl-5">
                    <Panel header={"Lyubkin and Patutin №21382"}>
                        {this.props.store.getState().token !== null ?   <HomePage/> :<LoginForm/> }  {/*<HomePage/> :<LoginForm/> | <LoginForm/> : <HomePage/> */}
                </Panel>
                </div>

            </div>
        )
    }
}


export default App;

