import request from '../api/request';

export const approve = (leaveRecord) => {
    return request.patch('/admin/reviewLeave', leaveRecord);
};

export const disApprove = (leaveRecord) => {
    return request.post('/admin/rejectLeave', leaveRecord);
};
