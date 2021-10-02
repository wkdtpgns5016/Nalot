import React from 'react'
import { useHistory, useLocation } from "react-router-dom";
import Header from "../Components/Header";
import Menu from "../Components/Menu";
import reactCSS from 'reactcss'
import {SketchPicker} from 'react-color'
import axios from 'axios'
import Button from "@material-ui/core/Button";
import Color from "../Components/Color";
import Footer from "../Components/Footer";

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

    const prevClicked = () =>{
        history.push('/nalot/recommendation',{
            "key": location.state.key,

            "current":location.state.current,
            "data":location.state.data
        })
    }

    const btnClicked = (event) =>{
        console.log(event.currentTarget.value)
        console.log(location.state)
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
            "selectedColor" : event.currentTarget.value,
            //사용자 선택 색
            "color":hex,

            "baseDate" : location.state.baseDate,
            "baseTime" : location.state.baseTime,


        })
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

            }).catch(err=>{
                alert(err.response.data.error)
                history.push('/nalot')
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

            return(
                    <Button name="button" style={
                        {
                            background: `${rgbToHex(red,green,blue)}`,
                            width: '100px',
                        }
                    }
                            value={`${rgbToHex(red,green,blue)}`}
                            onClick={btnClicked}
                    >
                        {rgbToHex(red,green,blue)}
                    </Button>

            )
        })
    }

    function renderToneOnTone(){
        arr2 = toneontone
        return arr2.map(({red,green,blue})=>{
            return(
                    <Button name="button" style={
                        {
                            background: `${rgbToHex(red,green,blue)}`,
                            width: '100px',
                        }

                    }
                            value={`${rgbToHex(red,green,blue)}`}
                            onClick={e=>btnClicked(e,"value")}
                    >
                        {`${rgbToHex(red,green,blue)}`}
                    </Button>

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
        },
    });
    return(

        <div>
            <Header/>
            <Menu/>
            <h1>의상 추천</h1>
            <Color/>
            <div>
                <h1>버튼을 눌러 색 선택하기</h1>
                <p>
                    <div style={ styles.swatch } onClick={openClicked}>
                        <div style={ styles.color } />
                    </div>
                    { displayColorPicker ? <div style={ styles.popover }>
                        <div style={ styles.cover } onClick={closeClicked}/>
                        <SketchPicker color={ color } onChange={ handleChange } />
                    </div> : null }
                </p>

            </div>
            <div>
                선택한 의상 : <h2>{location.state.clothes}</h2>
            </div>
            <div>
                <h1>원하는 색의 버튼을 누르세요</h1>
            </div>
            <div>
                톤인톤매치
                {renderToneInTone()}
            </div>
            <p/>
            <div>
                톤온톤매치
                {renderToneOnTone()}
            </div>
            <div>
                <Button
                    onClick={prevClicked}
                    variant="outlined"
                >이전</Button>
            </div>
            <Footer/>
        </div>
    )
}

export default recommendation2;