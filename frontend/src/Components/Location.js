import React from 'react'
import {Box, Paper, styled} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";

const Location = () =>{
    const Item = styled(Paper)(({theme})=>({
        ...theme.typography.body2,
        padding: theme.spacing(1),
        textAlign: 'center',
        color: 'black',
        backgroundColor:'#3975ea'

    }))

    const Item2 = styled(Paper)(({theme})=>({
        ...theme.typography.body2,
        padding: theme.spacing(1),
        textAlign: 'center',
        color: 'black',
        backgroundColor:'#6b99f2'

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
                        <Item>지역선택</Item>
                    </Grid>
                    <Grid item xs={3}>
                        <Item2>의상선택</Item2>
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

export default Location;