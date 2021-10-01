import React from 'react'
import {Box, Paper, styled} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";

const Clothes = () =>{
    const Item2 = styled(Paper)(({theme})=>({
        ...theme.typography.body2,
        padding: theme.spacing(1),
        textAlign: 'center',
        color: 'black',
        backgroundColor:'#2ecc71'

    }))

    const Item = styled(Paper)(({theme})=>({
        ...theme.typography.body2,
        padding: theme.spacing(1),
        textAlign: 'center',
        color: 'black',
        backgroundColor:'#1f9451'

    }))

    return(
        <div
            style={{
                marginLeft:'10%'
            }}
        >
            <Box sx={{ flexGrow: 1 }}>
                <Grid container spacing={0}>
                    <Grid item xs={3}
                    >
                        <Item2>지역선택</Item2>
                    </Grid>
                    <Grid item xs={3}>
                        <Item>의상선택</Item>
                    </Grid>
                    <Grid item xs={3}>
                        <Item2>색상선택</Item2>
                    </Grid>
                    <Grid item xs={3}>
                        <Item2>최종결과</Item2>
                    </Grid>
                </Grid>
            </Box>
        </div>
    )
}

export default Clothes;