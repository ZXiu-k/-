import request from '../api/request';

export const updateEmp = (row) => {
    return request.post('/admin/updateEmp',row);
};