import request from '../api/request';

export const getLeave = (page = 1, rows = 10) => {
    return request.get('/admin/getLeaveRecords', { params: { page, rows } });
};