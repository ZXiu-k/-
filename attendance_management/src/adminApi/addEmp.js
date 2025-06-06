import request from '../api/request';

export const addEmp = (data) => {
    return request.post('/admin/addEmp', data)
}

