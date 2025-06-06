import request from '../api/request';

export const getAllEmps = (page = 1, rows = 10) => {
    return request.get('/admin/getAllEmps', { params: { page, rows } });
};