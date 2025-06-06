import request from '../api/request';

export const getRecords = (page = 1, rows = 20) => {
    return request.get('/getRecords', { params: { page, rows } });
};