import request from '../api/request';

export const getAllRecords = (page = 1, rows = 10) => {
    return request.get('/admin/getAllRecords', { params: { page, rows } });
};