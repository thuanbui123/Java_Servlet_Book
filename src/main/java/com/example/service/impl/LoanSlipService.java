package com.example.service.impl;

import com.example.dao.impl.AccountDAO;
import com.example.dao.impl.BookDAO;
import com.example.dao.impl.LoanSlipDAO;
import com.example.model.AccountModel;
import com.example.model.BookModel;
import com.example.model.LoanSlipModel;
import com.example.service.ILoanSlipService;
import com.example.utils.HttpUtil;
import com.example.utils.ResponseAPIUtils;
import com.example.wrapper.WrapperResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LoanSlipService implements ILoanSlipService {
    private final LoanSlipDAO loanSlipDAO = new LoanSlipDAO();

    private final ResponseAPIUtils<LoanSlipModel> responseAPIUtils = new ResponseAPIUtils<>();
    @Override
    public List<LoanSlipModel> findAll() {
        return loanSlipDAO.findALl();
    }

    @Override
    public List<LoanSlipModel> findOneByIdLoanSlip(String id) {
        return loanSlipDAO.findOneByCode(id);
    }

    @Override
    public List<LoanSlipModel> findByIdLoanSlipAndIdAccount(String idLoanSlip, int idAccount) {
        return loanSlipDAO.findByIdLoanSlipAndIdAccount(idLoanSlip, idAccount);
    }

    @Override
    public LoanSlipModel save(LoanSlipModel loanSlipModel) {
        boolean isExistBook = loanSlipDAO.isExistBookInLoanSlip(loanSlipModel.getCode(), loanSlipModel.getIdBook());
        if(!isExistBook) {
            Long id = loanSlipDAO.addLoanSlip(loanSlipModel);
            return loanSlipDAO.findOneById(Integer.parseInt(id.toString()));
        }
        return null;
    }

    @Override
    public LoanSlipModel update(LoanSlipModel loanSlipModel, int id) {
        loanSlipDAO.updateLoanSlip(loanSlipModel, id);
        return findOneById((long) id);
    }

    @Override
    public void delete(int id) {
        loanSlipDAO.deleteLoanSlip(id);
    }

    public void deleteData (String pathInfo,HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        WrapperResponse<LoanSlipModel> wrapperResponse = new WrapperResponse<>();
        if(pathInfo != null && !pathInfo.isEmpty()) {
            String[]path = pathInfo.split("/");
            if(path.length == 3) {
                if(path[1].equals("delete")) {
                    int id = Integer.parseInt(path[2]);
                    LoanSlipModel findLoanSlip = findOneById((long) id);
                    if(findLoanSlip != null) {
                        delete(id);
                        responseAPIUtils.deleteSuccess(wrapperResponse, resp);
                    } else {
                        responseAPIUtils.notFoundAPI(wrapperResponse, resp);
                    }
                } else {
                    responseAPIUtils.notFoundAPI(wrapperResponse, resp);
                }
            }
        } else {
            responseAPIUtils.notFoundAPI(wrapperResponse, resp);
        }
        mapper.writeValue(resp.getOutputStream(), wrapperResponse);
    }

    public void updateData(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        WrapperResponse<LoanSlipModel> wrapperResponse = new WrapperResponse<>();

        LoanSlipModel loanSlipModel = HttpUtil.of(req.getReader()).toModel(LoanSlipModel.class);
        if (pathInfo != null && !pathInfo.isEmpty()) {
            String[] path = pathInfo.split("/");
            if (path.length == 3) {
                if (path[1].equals("update")) {
                    int id = Integer.parseInt(path[2]);
                    LoanSlipModel saveLoanSlip = update(loanSlipModel, id);
                    ArrayList<LoanSlipModel> list = new ArrayList<>();
                    list.add(saveLoanSlip);
                    responseAPIUtils.updateSuccess(wrapperResponse, list, resp);
                }
            } else {
                responseAPIUtils.ServerError(wrapperResponse, resp);
            }
        } else {
            responseAPIUtils.notFoundAPI(wrapperResponse, resp);
        }
        mapper.writeValue(resp.getOutputStream(), wrapperResponse);
    }

    public void insertData(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        WrapperResponse<LoanSlipModel> wrapperResponse = new WrapperResponse<>();
        LoanSlipModel loanSlipModel = HttpUtil.of(req.getReader()).toModel(LoanSlipModel.class);
        if (loanSlipModel.getIdBook() == 0 || loanSlipModel.getCode() == null || loanSlipModel.getCode().isEmpty() || loanSlipModel.getIdAccount() == 0) {
            responseAPIUtils.requiredDataAPI(wrapperResponse, resp);
        } else {
            LoanSlipModel saveLoanSlip = save(loanSlipModel);
            if(saveLoanSlip != null) {
                ArrayList<LoanSlipModel> list = new ArrayList<>();
                list.add(saveLoanSlip);
                responseAPIUtils.insertSuccess(wrapperResponse, list, resp);
            } else {
                responseAPIUtils.duplicateDataAPI(wrapperResponse, resp);
            }
        }
        mapper.writeValue(resp.getOutputStream(), wrapperResponse);
    }

    public LoanSlipModel findOneById (Long id) {
        LoanSlipModel loanSlipModel = (LoanSlipModel) loanSlipDAO.findOneById(Integer.parseInt(id.toString()));
        if(loanSlipModel !=null) {
            return loanSlipModel;
        } else {
            return null;
        }
    }

    public void findData(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        ArrayList<LoanSlipModel> list = new ArrayList<>();
        WrapperResponse<LoanSlipModel> wrapperResponse = new WrapperResponse<>();

        if(pathInfo == null || pathInfo.equals("/")) {
            list = (ArrayList<LoanSlipModel>) findAll();
        } else {
            String[] path = pathInfo.split("/");
            if(path.length == 5) {
                String idLoanSlipStr = path[2];
                String idAccountStr = path[4];

                int idAccount = 0;
                try {
                    idAccount = Integer.parseInt(idAccountStr);
                } catch (NumberFormatException ex) {
                    mapper.writeValue(resp.getOutputStream(), ex);
                }
                list = (ArrayList<LoanSlipModel>) findByIdLoanSlipAndIdAccount(idLoanSlipStr, idAccount);
            } else if(path.length == 3) {
                String idLoanSlipStr = path[2];
                list = (ArrayList<LoanSlipModel>) findOneByIdLoanSlip(String.valueOf(idLoanSlipStr));
            }
        }
        if(list != null && !list.isEmpty()) {
            responseAPIUtils.getDataSuccess(wrapperResponse, list, resp);
        }
        mapper.writeValue(resp.getOutputStream(), wrapperResponse);
    }
}
