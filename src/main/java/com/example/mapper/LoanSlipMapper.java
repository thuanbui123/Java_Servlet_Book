package com.example.mapper;

import com.example.model.BookModel;
import com.example.model.LoanSlipModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanSlipMapper implements RowMapper<LoanSlipModel>{
    @Override
    public LoanSlipModel mapRow(ResultSet rs) {
        try {

            LoanSlipModel loanSlipModel = new LoanSlipModel();
            loanSlipModel.setId((int) rs.getLong("loanSlip.id"));
            loanSlipModel.setCode(rs.getString("code"));
            loanSlipModel.setIdAccount((int) rs.getLong("idAccount"));
            loanSlipModel.setIdBook((int) rs.getLong("idBook"));
            loanSlipModel.setCreated_at(rs.getTimestamp("loanSlip.created_at"));
            loanSlipModel.setUpdated_at(rs.getTimestamp("loanSlip.updated_at"));
            loanSlipModel.setTitle(rs.getString("title"));
            loanSlipModel.setUserName(rs.getString("username"));
            loanSlipModel.setNumberPhone(rs.getString("numberPhone"));

            return loanSlipModel;
        }catch (SQLException e) {
            return null;
        }
    }
}