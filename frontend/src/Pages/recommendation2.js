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

    const[toneintone, settoneintone] = React.useState(location.state.toneintone)
    const[toneontone, settoneontone] = React.useState(location.state.toneontone)

    let arr=[]
    let arr2=[]

    let red = 253
    let green =226
    let blue = 237

    let selectedColor

    const prevClicked = () =>{
        history.push('/nalot/recommendation',{
            "key": location.state.key,

            "current":location.state.current,
            "data":location.state.data
        })
    }

    const nextClicked = () =>{
        let count = document.getElementsByName("radio").length
        for (let i =0; i<count; i++){
            if(document.getElementsByName("radio")[i].checked===true){
                selectedColor = (document.getElementsByName("radio")[i].value)
            }
        }
        if(selectedColor === undefined){

        }else{

            history.push('/nalot/recommendation3',{
                "key": location.state.key,
                "email": location.state.email,

                "current":location.state.current,
                "data":location.state.data,
                "clothes" : location.state.clothes,
                "clothes_number" : location.state.clothes_number,
                "toneintone" : toneintone,
                "toneontone" : toneontone,
                //추천 색
                "selectedColor" : selectedColor,
                //사용자 선택 색
                "color":hex


            })
        }

    }

    const openClicked = () =>{
        setdisplayColorPicker(true)
    }

    const closeClicked = () =>{
        setdisplayColorPicker(false)
        axios.post('http://localhost:8080/colors/tone-on-tone-mixes',{
            "hexCode" : hex
        },{
            headers:{
                'Content-Type':'application/json',
                'Authorization':`${location.state.key}`
            }
        }).then(response=>{

            settoneintone(response.data)

            axios.post('http://localhost:8080/colors/tone-in-tone-mixes',{
                "hexCode":hex
            },{
                headers:{
                    'Content-Type':'application/json',
                    'Authorization':`${location.state.key}`
                }
            }).then(res=>{
                settoneontone(res.data)

            })
        })
    }

    const handleChange = (color) =>{
        setcolor(color.rgb)
        sethex(color.hex)
    }

    function componentToHex(c) {
        let hex = c.toString(16);
        return hex.length === 1 ? "0" + hex : hex;
    }

    function rgbToHex(r, g, b) {
        return "#" + componentToHex(r) + componentToHex(g) + componentToHex(b);
    }

    function renderToneInTone(){
        arr = toneintone

        return arr.map(({red,green,blue})=>{

            styles.button.background=rgbToHex(red,green,blue)

            return(
                    <button name="button" style={
                        {
                            background: `${rgbToHex(red,green,blue)}`
                        }
                    }>
                        {rgbToHex(red,green,blue)}
                    </button>

            )
        })
    }

    function renderToneOnTone(){
        arr2 = toneontone
        return arr2.map(({red,green,blue})=>{
            return(
                    <button name="button" style={
                        {
                            background: `${rgbToHex(red,green,blue)}`
                        }
                    }>
                        {rgbToHex(red,green,blue)}
                    </button>

            )
        })
    }

    function renderRadio(){
        arr = toneintone
        return arr.map(({red,green,blue})=>{
            return(
                <label>
                    <input
                        type="radio"
                        name="radio"
                        value={`${rgbToHex(red,green,blue)}`}
                        />
                </label>
            )
        })
    }
    function renderRadio2(){
        arr2 = toneontone
        return arr2.map(({red,green,blue})=>{
            return(
                <label>
                    <input
                        type="radio"
                        name="radio"
                        value={`${rgbToHex(red,green,blue)}`}
                    />
                </label>
            )
        })
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
            button:{
                background: `${rgbToHex(red,green,blue)}`
            },
            button2:{
                background:`${rgbToHex(red,green,blue)}`
            }
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
                선택한 의상 : {location.state.clothes}
            </div>
            <div>
                톤인톤매치
                {renderToneInTone()}
                <div>
                    {renderRadio()}
                </div>
            </div>
            <div>
                톤온톤매치
                {renderToneOnTone()}
                <div>
                    {renderRadio2()}
                </div>
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