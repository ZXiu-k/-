import request from '../api/request';

export const getLeave = (page = 1, rows = 20) => {
    return request.get('/getLeave', { params: { page, rows } });
};