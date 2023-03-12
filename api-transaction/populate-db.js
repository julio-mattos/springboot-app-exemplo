db.transaction.insertMany(
    [
        {_id: "0001", description: "payment receive 1", cpf:"10242322808", origin:"Fernando code", status:"PENDING", value: 100, createdDate: new Date("2022-06-08T11:04:35") },
        {_id: "0002", description: "payment receive 2", cpf:"12086611838", origin:"Bruna code", status:"PENDING", value: 200, createdDate: new Date("2022-06-08T11:04:35") },
        {_id: "0003", description: "payment receive 3", cpf:"34015215869", origin:"Julio code", status:"APPROVED", value: 300, createdDate: new Date("2022-06-08T11:04:35") }
    ]
);