import React from 'react'
import _ from "lodash";

const Pagination = (props) =>{
    const {itemsCount, pageSize, currentPage, onPageChange} = props;
    const pageCount = Math.ceil(itemsCount/pageSize)
    //console.log(itemsCount, pageSize)
    if(pageCount===1) return null;

    const pages = _.range(1, pageCount + 1)

    return (
        <nav> {}
            <ul className="pagination"
                style={{
                    "list-style":"none",
                    "text-align":"center"
                }}
            >
                {pages.map(page => (
                    <li key={page}
                        className={page === currentPage ? "page-item active" : "page-item"}
                        style={{ cursor: "pointer", display:"inline", padding:'10px',"text-align":"center" }}>
                        <a className="page-link" onClick={()=>onPageChange(page)}>{page}</a>
                    </li>
                ))}
            </ul>
        </nav>
    )

}

export default Pagination