package com.example.mapper;

import com.example.model.LoanSlipModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanSlipMapper implements RowMapper<LoanSlipModel>{
    @Override
    public LoanSlipModel mapRow(ResultSet rs) {
        try {
            LoanSlipModel loanSlipModel = new LoanSlipModel();
            loanSlipModel.setId((int) rs.getLong("id"));
            loanSlipModel.setIdAccount((int) rs.getLong("idAccount"));
            loanSlipModel.setIdBook((int) rs.getLong("idBook"));
            loanSlipModel.setCode(rs.getString("code"));
            loanSlipModel.setCreated_at(rs.getTimestamp("created_at"));
            loanSlipModel.setUpdated_at(rs.getTimestamp("updated_at"));

            return loanSlipModel;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
