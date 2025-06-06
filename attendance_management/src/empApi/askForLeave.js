import request from '../api/request';

export const askForLeave = (data) => {
    return request.post('/askForLeave', data);
};