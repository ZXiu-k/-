import request from '../api/request';

export const deleteEmp = (data) => {
    return request.delete('/admin/deleteEmp', { data });
};