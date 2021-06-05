import sqlite3


class DBConnector:
    def __init__(self):
        self.conn = sqlite3.connect('../../../../../../../mo.db')

    def connect(self):
        self.conn = sqlite3.connect('../../../../../../../mo.db')

    def disconnect(self):
        self.conn.close()

    def is_not_present(self, client_id):
        cursor = self.conn.execute("select * from clients where id = ?", (client_id,))
        return cursor.fetchone() is None

    def add_new_client(self, name, client_id):
        self.conn.execute("insert into clients (id, name, waiting, response, collected) values (?, ?, ?, ?, ?)",
                          (client_id, name, 0, None, 1))
        self.conn.commit()

    def is_waiting(self, client_id):
        cursor = self.conn.execute("select waiting from clients where id = ?", (client_id,))
        waiting = 0
        for row in cursor:
            waiting = row[0]
        return waiting

    def is_not_collected(self, client_id):
        cursor = self.conn.execute("select collected from clients where id = ?", (client_id,))
        collected = 1
        for row in cursor:
            collected = row[0]
        return collected == 0

    def get_last_response(self, client_id):
        cursor = self.conn.execute("select response from clients where id = ?", (client_id,))
        response = ""
        for row in cursor:
            response = row[0]
        return response

    def set_collected(self, client_id):
        self.conn.execute("update clients set collected = ? where id = ?", (1, client_id))
        self.conn.commit()
