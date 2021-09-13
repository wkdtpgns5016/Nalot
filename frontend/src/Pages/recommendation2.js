import React from 'react'
import { useHistory, useLocation } from "react-router-dom";
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import reactCSS from 'reactcss'
import {SketchPicker} from 'react-color'
import axios from 'axios'

function recommendation2(){
    const history = useHistory();
    const location = useLocation();
    const [displayColorPicker, setdisplayColorPicker] = React.useState(false);
    const [color, setcolor] = React.useState(
        {
            r : '241',
            g : '112',
            b : '19',
            a : '1'
        }
    )
    const [hex, sethex] = React.useState('#f17013')

    const prevClicked = () =>{
        history.push('/nalot/recommendation',{
            "key": location.state.key,

            "current":location.state.current,
            "data":location.state.data
        })
    }

    const nextClicked = () =>{
        history.push('/nalot/recommendation3',{
            "key": location.state.key,
            "email": location.state.email,

            "current":location.state.current,
            "data":location.state.data

        })
    }

    const openClicked = () =>{
        setdisplayColorPicker(true)
    }

    const closeClicked = () =>{
        setdisplayColorPicker(false)
        console.log(hex)
        axios.post('http://localhost:8080/colors/tone-on-tone-mixes',{
            "hexCode" : hex
        },{
            headers:{
                'Content-Type':'application/json',
                'Authorization':`${location.state.key}`
            }
        }).then(response=>{
            console.log(response.data)
            axios.post('http://localhost:8080/colors/tone-in-tone-mixes',{
                "hexCode":hex
            },{
                headers:{
                    'Content-Type':'application/json',
                    'Authorization':`${location.state.key}`
                }
            }).then(res=>{
                console.log(res.data)
            })
        })
    }

    const handleChange = (color) =>{
        setcolor(color.rgb)
        //console.log(color.hex)
        sethex(color.hex)
    }


    const styles = reactCSS({
        'default': {
            color: {
                width: '36px',
                height: '14px',
                borderRadius: '2px',
                background: `rgba(${ color.r }, ${ color.g }, ${ color.b }, ${ color.a })`,
            },
            swatch: {
                padding: '5px',
                background: '#fff',
                borderRadius: '1px',
                boxShadow: '0 0 0 1px rgba(0,0,0,.1)',
                display: 'inline-block',
                cursor: 'pointer',
            },
            popover: {
                position: 'absolute',
                zIndex: '2',
            },
            cover: {
                position: 'fixed',
                top: '0px',
                right: '0px',
                bottom: '0px',
                left: '0px',
            },
        },
    });
    return(

        <div>
            <Header/>
            <Menu/>
            <div>
                <div style={ styles.swatch } onClick={openClicked}>
                    <div style={ styles.color } />
                </div>
                { displayColorPicker ? <div style={ styles.popover }>
                    <div style={ styles.cover } onClick={closeClicked}/>
                    <SketchPicker color={ color } onChange={ handleChange } />
                </div> : null }

            </div>
            <div>
                <button
                    onClick={prevClicked}
                >이전</button>
                <button
                    onClick={nextClicked}
                >다음</button>

            </div>
        </div>
    )
}

export default recommendation2;